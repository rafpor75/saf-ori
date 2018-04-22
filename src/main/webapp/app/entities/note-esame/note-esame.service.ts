import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { NoteEsame } from './note-esame.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<NoteEsame>;

@Injectable()
export class NoteEsameService {

    private resourceUrl =  SERVER_API_URL + 'api/note-esames';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) { }

    create(noteEsame: NoteEsame): Observable<EntityResponseType> {
        const copy = this.convert(noteEsame);
        return this.http.post<NoteEsame>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(noteEsame: NoteEsame): Observable<EntityResponseType> {
        const copy = this.convert(noteEsame);
        return this.http.put<NoteEsame>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<NoteEsame>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<NoteEsame[]>> {
        const options = createRequestOption(req);
        return this.http.get<NoteEsame[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<NoteEsame[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: NoteEsame = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<NoteEsame[]>): HttpResponse<NoteEsame[]> {
        const jsonResponse: NoteEsame[] = res.body;
        const body: NoteEsame[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to NoteEsame.
     */
    private convertItemFromServer(noteEsame: NoteEsame): NoteEsame {
        const copy: NoteEsame = Object.assign({}, noteEsame);
        copy.dataDispensa = this.dateUtils
            .convertLocalDateFromServer(noteEsame.dataDispensa);
        copy.dataCorsi = this.dateUtils
            .convertLocalDateFromServer(noteEsame.dataCorsi);
        copy.dataABI = this.dateUtils
            .convertLocalDateFromServer(noteEsame.dataABI);
        copy.dataRiepilogo = this.dateUtils
            .convertLocalDateFromServer(noteEsame.dataRiepilogo);
        copy.oraEsame = this.dateUtils
            .convertDateTimeFromServer(noteEsame.oraEsame);
        return copy;
    }

    /**
     * Convert a NoteEsame to a JSON which can be sent to the server.
     */
    private convert(noteEsame: NoteEsame): NoteEsame {
        const copy: NoteEsame = Object.assign({}, noteEsame);
        copy.dataDispensa = this.dateUtils
            .convertLocalDateToServer(noteEsame.dataDispensa);
        copy.dataCorsi = this.dateUtils
            .convertLocalDateToServer(noteEsame.dataCorsi);
        copy.dataABI = this.dateUtils
            .convertLocalDateToServer(noteEsame.dataABI);
        copy.dataRiepilogo = this.dateUtils
            .convertLocalDateToServer(noteEsame.dataRiepilogo);

        copy.oraEsame = this.dateUtils.toDate(noteEsame.oraEsame);
        return copy;
    }
}
