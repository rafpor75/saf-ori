import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { Esami } from './esami.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Esami>;

@Injectable()
export class EsamiService {

    private resourceUrl =  SERVER_API_URL + 'api/esamis';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) { }

    create(esami: Esami): Observable<EntityResponseType> {
        const copy = this.convert(esami);
        return this.http.post<Esami>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(esami: Esami): Observable<EntityResponseType> {
        const copy = this.convert(esami);
        return this.http.put<Esami>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Esami>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Esami[]>> {
        const options = createRequestOption(req);
        return this.http.get<Esami[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Esami[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Esami = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Esami[]>): HttpResponse<Esami[]> {
        const jsonResponse: Esami[] = res.body;
        const body: Esami[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Esami.
     */
    private convertItemFromServer(esami: Esami): Esami {
        const copy: Esami = Object.assign({}, esami);
        copy.data = this.dateUtils
            .convertLocalDateFromServer(esami.data);
        return copy;
    }

    /**
     * Convert a Esami to a JSON which can be sent to the server.
     */
    private convert(esami: Esami): Esami {
        const copy: Esami = Object.assign({}, esami);
        copy.data = this.dateUtils
            .convertLocalDateToServer(esami.data);
        return copy;
    }
}
