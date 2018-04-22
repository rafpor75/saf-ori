import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { Studenti } from './studenti.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Studenti>;

@Injectable()
export class StudentiService {

    private resourceUrl =  SERVER_API_URL + 'api/studentis';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) { }

    create(studenti: Studenti): Observable<EntityResponseType> {
        const copy = this.convert(studenti);
        return this.http.post<Studenti>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(studenti: Studenti): Observable<EntityResponseType> {
        const copy = this.convert(studenti);
        return this.http.put<Studenti>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Studenti>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Studenti[]>> {
        const options = createRequestOption(req);
        return this.http.get<Studenti[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Studenti[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Studenti = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Studenti[]>): HttpResponse<Studenti[]> {
        const jsonResponse: Studenti[] = res.body;
        const body: Studenti[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Studenti.
     */
    private convertItemFromServer(studenti: Studenti): Studenti {
        const copy: Studenti = Object.assign({}, studenti);
        copy.dataDiNascita = this.dateUtils
            .convertLocalDateFromServer(studenti.dataDiNascita);
        copy.dataModifica = this.dateUtils
            .convertLocalDateFromServer(studenti.dataModifica);
        return copy;
    }

    /**
     * Convert a Studenti to a JSON which can be sent to the server.
     */
    private convert(studenti: Studenti): Studenti {
        const copy: Studenti = Object.assign({}, studenti);
        copy.dataDiNascita = this.dateUtils
            .convertLocalDateToServer(studenti.dataDiNascita);
        copy.dataModifica = this.dateUtils
            .convertLocalDateToServer(studenti.dataModifica);
        return copy;
    }
}
