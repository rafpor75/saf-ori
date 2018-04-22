import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { Tutor } from './tutor.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Tutor>;

@Injectable()
export class TutorService {

    private resourceUrl =  SERVER_API_URL + 'api/tutors';

    constructor(private http: HttpClient) { }

    create(tutor: Tutor): Observable<EntityResponseType> {
        const copy = this.convert(tutor);
        return this.http.post<Tutor>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(tutor: Tutor): Observable<EntityResponseType> {
        const copy = this.convert(tutor);
        return this.http.put<Tutor>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Tutor>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Tutor[]>> {
        const options = createRequestOption(req);
        return this.http.get<Tutor[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Tutor[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Tutor = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Tutor[]>): HttpResponse<Tutor[]> {
        const jsonResponse: Tutor[] = res.body;
        const body: Tutor[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Tutor.
     */
    private convertItemFromServer(tutor: Tutor): Tutor {
        const copy: Tutor = Object.assign({}, tutor);
        return copy;
    }

    /**
     * Convert a Tutor to a JSON which can be sent to the server.
     */
    private convert(tutor: Tutor): Tutor {
        const copy: Tutor = Object.assign({}, tutor);
        return copy;
    }
}
