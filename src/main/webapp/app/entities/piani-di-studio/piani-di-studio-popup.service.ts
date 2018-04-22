import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { PianiDiStudio } from './piani-di-studio.model';
import { PianiDiStudioService } from './piani-di-studio.service';

@Injectable()
export class PianiDiStudioPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private pianiDiStudioService: PianiDiStudioService

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
                this.pianiDiStudioService.find(id)
                    .subscribe((pianiDiStudioResponse: HttpResponse<PianiDiStudio>) => {
                        const pianiDiStudio: PianiDiStudio = pianiDiStudioResponse.body;
                        if (pianiDiStudio.dataModifica) {
                            pianiDiStudio.dataModifica = {
                                year: pianiDiStudio.dataModifica.getFullYear(),
                                month: pianiDiStudio.dataModifica.getMonth() + 1,
                                day: pianiDiStudio.dataModifica.getDate()
                            };
                        }
                        this.ngbModalRef = this.pianiDiStudioModalRef(component, pianiDiStudio);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.pianiDiStudioModalRef(component, new PianiDiStudio());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    pianiDiStudioModalRef(component: Component, pianiDiStudio: PianiDiStudio): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.pianiDiStudio = pianiDiStudio;
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
