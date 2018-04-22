import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { PianiDiStudio } from './piani-di-studio.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<PianiDiStudio>;

@Injectable()
export class PianiDiStudioService {

    private resourceUrl =  SERVER_API_URL + 'api/piani-di-studios';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) { }

    create(pianiDiStudio: PianiDiStudio): Observable<EntityResponseType> {
        const copy = this.convert(pianiDiStudio);
        return this.http.post<PianiDiStudio>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(pianiDiStudio: PianiDiStudio): Observable<EntityResponseType> {
        const copy = this.convert(pianiDiStudio);
        return this.http.put<PianiDiStudio>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<PianiDiStudio>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<PianiDiStudio[]>> {
        const options = createRequestOption(req);
        return this.http.get<PianiDiStudio[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<PianiDiStudio[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: PianiDiStudio = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<PianiDiStudio[]>): HttpResponse<PianiDiStudio[]> {
        const jsonResponse: PianiDiStudio[] = res.body;
        const body: PianiDiStudio[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to PianiDiStudio.
     */
    private convertItemFromServer(pianiDiStudio: PianiDiStudio): PianiDiStudio {
        const copy: PianiDiStudio = Object.assign({}, pianiDiStudio);
        copy.dataModifica = this.dateUtils
            .convertLocalDateFromServer(pianiDiStudio.dataModifica);
        return copy;
    }

    /**
     * Convert a PianiDiStudio to a JSON which can be sent to the server.
     */
    private convert(pianiDiStudio: PianiDiStudio): PianiDiStudio {
        const copy: PianiDiStudio = Object.assign({}, pianiDiStudio);
        copy.dataModifica = this.dateUtils
            .convertLocalDateToServer(pianiDiStudio.dataModifica);
        return copy;
    }
}
