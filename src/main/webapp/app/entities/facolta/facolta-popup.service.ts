import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { Facolta } from './facolta.model';
import { FacoltaService } from './facolta.service';

@Injectable()
export class FacoltaPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private facoltaService: FacoltaService

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
                this.facoltaService.find(id)
                    .subscribe((facoltaResponse: HttpResponse<Facolta>) => {
                        const facolta: Facolta = facoltaResponse.body;
                        if (facolta.dataModifica) {
                            facolta.dataModifica = {
                                year: facolta.dataModifica.getFullYear(),
                                month: facolta.dataModifica.getMonth() + 1,
                                day: facolta.dataModifica.getDate()
                            };
                        }
                        this.ngbModalRef = this.facoltaModalRef(component, facolta);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.facoltaModalRef(component, new Facolta());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    facoltaModalRef(component: Component, facolta: Facolta): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.facolta = facolta;
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
