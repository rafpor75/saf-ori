import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { Studenti } from './studenti.model';
import { StudentiService } from './studenti.service';

@Injectable()
export class StudentiPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private studentiService: StudentiService

    ) {
        this.ngbModalRef = null;
    }

    open(component: Component, id?: number | any): Promise<NgbModalRef> {
        return new Promise<NgbModalRef>((resolve, reject) => {
            const isOpen = this.ngbModalRef !== null;
            if (isOpen) {
                resolve(this.ngbModalRef);
            }

            if (id) {
                this.studentiService.find(id)
                    .subscribe((studentiResponse: HttpResponse<Studenti>) => {
                        const studenti: Studenti = studentiResponse.body;
                        if (studenti.dataDiNascita) {
                            studenti.dataDiNascita = {
                                year: studenti.dataDiNascita.getFullYear(),
                                month: studenti.dataDiNascita.getMonth() + 1,
                                day: studenti.dataDiNascita.getDate()
                            };
                        }
                        if (studenti.dataModifica) {
                            studenti.dataModifica = {
                                year: studenti.dataModifica.getFullYear(),
                                month: studenti.dataModifica.getMonth() + 1,
                                day: studenti.dataModifica.getDate()
                            };
                        }
                        this.ngbModalRef = this.studentiModalRef(component, studenti);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.studentiModalRef(component, new Studenti());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    studentiModalRef(component: Component, studenti: Studenti): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.studenti = studenti;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        });
        return modalRef;
    }
}
