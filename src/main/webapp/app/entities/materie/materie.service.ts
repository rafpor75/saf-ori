import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { Materie } from './materie.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Materie>;

@Injectable()
export class MaterieService {

    private resourceUrl =  SERVER_API_URL + 'api/materies';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) { }

    create(materie: Materie): Observable<EntityResponseType> {
        const copy = this.convert(materie);
        return this.http.post<Materie>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(materie: Materie): Observable<EntityResponseType> {
        const copy = this.convert(materie);
        return this.http.put<Materie>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Materie>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Materie[]>> {
        const options = createRequestOption(req);
        return this.http.get<Materie[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Materie[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Materie = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Materie[]>): HttpResponse<Materie[]> {
        const jsonResponse: Materie[] = res.body;
        const body: Materie[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Materie.
     */
    private convertItemFromServer(materie: Materie): Materie {
        const copy: Materie = Object.assign({}, materie);
        copy.dataModifica = this.dateUtils
            .convertLocalDateFromServer(materie.dataModifica);
        return copy;
    }

    /**
     * Convert a Materie to a JSON which can be sent to the server.
     */
    private convert(materie: Materie): Materie {
        const copy: Materie = Object.assign({}, materie);
        copy.dataModifica = this.dateUtils
            .convertLocalDateToServer(materie.dataModifica);
        return copy;
    }
}
