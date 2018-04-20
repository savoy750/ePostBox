import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { DataReferenceMySuffix } from './data-reference-my-suffix.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<DataReferenceMySuffix>;

@Injectable()
export class DataReferenceMySuffixService {

    private resourceUrl =  SERVER_API_URL + 'api/data-references';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/data-references';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) { }

    create(dataReference: DataReferenceMySuffix): Observable<EntityResponseType> {
        const copy = this.convert(dataReference);
        return this.http.post<DataReferenceMySuffix>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(dataReference: DataReferenceMySuffix): Observable<EntityResponseType> {
        const copy = this.convert(dataReference);
        return this.http.put<DataReferenceMySuffix>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<DataReferenceMySuffix>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<DataReferenceMySuffix[]>> {
        const options = createRequestOption(req);
        return this.http.get<DataReferenceMySuffix[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<DataReferenceMySuffix[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    search(req?: any): Observable<HttpResponse<DataReferenceMySuffix[]>> {
        const options = createRequestOption(req);
        return this.http.get<DataReferenceMySuffix[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<DataReferenceMySuffix[]>) => this.convertArrayResponse(res));
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: DataReferenceMySuffix = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<DataReferenceMySuffix[]>): HttpResponse<DataReferenceMySuffix[]> {
        const jsonResponse: DataReferenceMySuffix[] = res.body;
        const body: DataReferenceMySuffix[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to DataReferenceMySuffix.
     */
    private convertItemFromServer(dataReference: DataReferenceMySuffix): DataReferenceMySuffix {
        const copy: DataReferenceMySuffix = Object.assign({}, dataReference);
        copy.dateDeNaissance = this.dateUtils
            .convertDateTimeFromServer(dataReference.dateDeNaissance);
        return copy;
    }

    /**
     * Convert a DataReferenceMySuffix to a JSON which can be sent to the server.
     */
    private convert(dataReference: DataReferenceMySuffix): DataReferenceMySuffix {
        const copy: DataReferenceMySuffix = Object.assign({}, dataReference);

        copy.dateDeNaissance = this.dateUtils.toDate(dataReference.dateDeNaissance);
        return copy;
    }
}
