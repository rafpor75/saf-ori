import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { AnnoAccademico } from './anno-accademico.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<AnnoAccademico>;

@Injectable()
export class AnnoAccademicoService {

    private resourceUrl =  SERVER_API_URL + 'api/anno-accademicos';

    constructor(private http: HttpClient) { }

    create(annoAccademico: AnnoAccademico): Observable<EntityResponseType> {
        const copy = this.convert(annoAccademico);
        return this.http.post<AnnoAccademico>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(annoAccademico: AnnoAccademico): Observable<EntityResponseType> {
        const copy = this.convert(annoAccademico);
        return this.http.put<AnnoAccademico>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<AnnoAccademico>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<AnnoAccademico[]>> {
        const options = createRequestOption(req);
        return this.http.get<AnnoAccademico[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<AnnoAccademico[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: AnnoAccademico = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<AnnoAccademico[]>): HttpResponse<AnnoAccademico[]> {
        const jsonResponse: AnnoAccademico[] = res.body;
        const body: AnnoAccademico[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to AnnoAccademico.
     */
    private convertItemFromServer(annoAccademico: AnnoAccademico): AnnoAccademico {
        const copy: AnnoAccademico = Object.assign({}, annoAccademico);
        return copy;
    }

    /**
     * Convert a AnnoAccademico to a JSON which can be sent to the server.
     */
    private convert(annoAccademico: AnnoAccademico): AnnoAccademico {
        const copy: AnnoAccademico = Object.assign({}, annoAccademico);
        return copy;
    }
}
