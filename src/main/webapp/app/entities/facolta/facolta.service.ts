import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { Facolta } from './facolta.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Facolta>;

@Injectable()
export class FacoltaService {

    private resourceUrl =  SERVER_API_URL + 'api/facoltas';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) { }

    create(facolta: Facolta): Observable<EntityResponseType> {
        const copy = this.convert(facolta);
        return this.http.post<Facolta>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(facolta: Facolta): Observable<EntityResponseType> {
        const copy = this.convert(facolta);
        return this.http.put<Facolta>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Facolta>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Facolta[]>> {
        const options = createRequestOption(req);
        return this.http.get<Facolta[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Facolta[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Facolta = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Facolta[]>): HttpResponse<Facolta[]> {
        const jsonResponse: Facolta[] = res.body;
        const body: Facolta[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Facolta.
     */
    private convertItemFromServer(facolta: Facolta): Facolta {
        const copy: Facolta = Object.assign({}, facolta);
        copy.dataModifica = this.dateUtils
            .convertLocalDateFromServer(facolta.dataModifica);
        return copy;
    }

    /**
     * Convert a Facolta to a JSON which can be sent to the server.
     */
    private convert(facolta: Facolta): Facolta {
        const copy: Facolta = Object.assign({}, facolta);
        copy.dataModifica = this.dateUtils
            .convertLocalDateToServer(facolta.dataModifica);
        return copy;
    }
}
