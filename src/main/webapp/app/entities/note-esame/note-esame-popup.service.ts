import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { DatePipe } from '@angular/common';
import { NoteEsame } from './note-esame.model';
import { NoteEsameService } from './note-esame.service';

@Injectable()
export class NoteEsamePopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private noteEsameService: NoteEsameService

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
                this.noteEsameService.find(id)
                    .subscribe((noteEsameResponse: HttpResponse<NoteEsame>) => {
                        const noteEsame: NoteEsame = noteEsameResponse.body;
                        if (noteEsame.dataDispensa) {
                            noteEsame.dataDispensa = {
                                year: noteEsame.dataDispensa.getFullYear(),
                                month: noteEsame.dataDispensa.getMonth() + 1,
                                day: noteEsame.dataDispensa.getDate()
                            };
                        }
                        if (noteEsame.dataCorsi) {
                            noteEsame.dataCorsi = {
                                year: noteEsame.dataCorsi.getFullYear(),
                                month: noteEsame.dataCorsi.getMonth() + 1,
                                day: noteEsame.dataCorsi.getDate()
                            };
                        }
                        if (noteEsame.dataABI) {
                            noteEsame.dataABI = {
                                year: noteEsame.dataABI.getFullYear(),
                                month: noteEsame.dataABI.getMonth() + 1,
                                day: noteEsame.dataABI.getDate()
                            };
                        }
                        if (noteEsame.dataRiepilogo) {
                            noteEsame.dataRiepilogo = {
                                year: noteEsame.dataRiepilogo.getFullYear(),
                                month: noteEsame.dataRiepilogo.getMonth() + 1,
                                day: noteEsame.dataRiepilogo.getDate()
                            };
                        }
                        noteEsame.oraEsame = this.datePipe
                            .transform(noteEsame.oraEsame, 'yyyy-MM-ddTHH:mm:ss');
                        this.ngbModalRef = this.noteEsameModalRef(component, noteEsame);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.noteEsameModalRef(component, new NoteEsame());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    noteEsameModalRef(component: Component, noteEsame: NoteEsame): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.noteEsame = noteEsame;
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
