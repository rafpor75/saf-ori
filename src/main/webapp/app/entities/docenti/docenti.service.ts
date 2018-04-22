import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { Docenti } from './docenti.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Docenti>;

@Injectable()
export class DocentiService {

    private resourceUrl =  SERVER_API_URL + 'api/docentis';

    constructor(private http: HttpClient) { }

    create(docenti: Docenti): Observable<EntityResponseType> {
        const copy = this.convert(docenti);
        return this.http.post<Docenti>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(docenti: Docenti): Observable<EntityResponseType> {
        const copy = this.convert(docenti);
        return this.http.put<Docenti>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Docenti>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Docenti[]>> {
        const options = createRequestOption(req);
        return this.http.get<Docenti[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Docenti[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Docenti = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Docenti[]>): HttpResponse<Docenti[]> {
        const jsonResponse: Docenti[] = res.body;
        const body: Docenti[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Docenti.
     */
    private convertItemFromServer(docenti: Docenti): Docenti {
        const copy: Docenti = Object.assign({}, docenti);
        return copy;
    }

    /**
     * Convert a Docenti to a JSON which can be sent to the server.
     */
    private convert(docenti: Docenti): Docenti {
        const copy: Docenti = Object.assign({}, docenti);
        return copy;
    }
}
