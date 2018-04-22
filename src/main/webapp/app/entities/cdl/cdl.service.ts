import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { Cdl } from './cdl.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Cdl>;

@Injectable()
export class CdlService {

    private resourceUrl =  SERVER_API_URL + 'api/cdls';

    constructor(private http: HttpClient) { }

    create(cdl: Cdl): Observable<EntityResponseType> {
        const copy = this.convert(cdl);
        return this.http.post<Cdl>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(cdl: Cdl): Observable<EntityResponseType> {
        const copy = this.convert(cdl);
        return this.http.put<Cdl>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Cdl>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Cdl[]>> {
        const options = createRequestOption(req);
        return this.http.get<Cdl[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Cdl[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Cdl = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Cdl[]>): HttpResponse<Cdl[]> {
        const jsonResponse: Cdl[] = res.body;
        const body: Cdl[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Cdl.
     */
    private convertItemFromServer(cdl: Cdl): Cdl {
        const copy: Cdl = Object.assign({}, cdl);
        return copy;
    }

    /**
     * Convert a Cdl to a JSON which can be sent to the server.
     */
    private convert(cdl: Cdl): Cdl {
        const copy: Cdl = Object.assign({}, cdl);
        return copy;
    }
}
