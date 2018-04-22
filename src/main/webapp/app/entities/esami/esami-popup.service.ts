import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { Esami } from './esami.model';
import { EsamiService } from './esami.service';

@Injectable()
export class EsamiPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private esamiService: EsamiService

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
                this.esamiService.find(id)
                    .subscribe((esamiResponse: HttpResponse<Esami>) => {
                        const esami: Esami = esamiResponse.body;
                        if (esami.data) {
                            esami.data = {
                                year: esami.data.getFullYear(),
                                month: esami.data.getMonth() + 1,
                                day: esami.data.getDate()
                            };
                        }
                        this.ngbModalRef = this.esamiModalRef(component, esami);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.esamiModalRef(component, new Esami());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    esamiModalRef(component: Component, esami: Esami): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.esami = esami;
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
