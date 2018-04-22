import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { Materie } from './materie.model';
import { MaterieService } from './materie.service';

@Injectable()
export class MateriePopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private materieService: MaterieService

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
                this.materieService.find(id)
                    .subscribe((materieResponse: HttpResponse<Materie>) => {
                        const materie: Materie = materieResponse.body;
                        if (materie.dataModifica) {
                            materie.dataModifica = {
                                year: materie.dataModifica.getFullYear(),
                                month: materie.dataModifica.getMonth() + 1,
                                day: materie.dataModifica.getDate()
                            };
                        }
                        this.ngbModalRef = this.materieModalRef(component, materie);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.materieModalRef(component, new Materie());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    materieModalRef(component: Component, materie: Materie): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.materie = materie;
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
