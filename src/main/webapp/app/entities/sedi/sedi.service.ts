import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { Sedi } from './sedi.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Sedi>;

@Injectable()
export class SediService {

    private resourceUrl =  SERVER_API_URL + 'api/sedis';

    constructor(private http: HttpClient) { }

    create(sedi: Sedi): Observable<EntityResponseType> {
        const copy = this.convert(sedi);
        return this.http.post<Sedi>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(sedi: Sedi): Observable<EntityResponseType> {
        const copy = this.convert(sedi);
        return this.http.put<Sedi>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Sedi>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Sedi[]>> {
        const options = createRequestOption(req);
        return this.http.get<Sedi[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Sedi[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Sedi = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Sedi[]>): HttpResponse<Sedi[]> {
        const jsonResponse: Sedi[] = res.body;
        const body: Sedi[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Sedi.
     */
    private convertItemFromServer(sedi: Sedi): Sedi {
        const copy: Sedi = Object.assign({}, sedi);
        return copy;
    }

    /**
     * Convert a Sedi to a JSON which can be sent to the server.
     */
    private convert(sedi: Sedi): Sedi {
        const copy: Sedi = Object.assign({}, sedi);
        return copy;
    }
}
