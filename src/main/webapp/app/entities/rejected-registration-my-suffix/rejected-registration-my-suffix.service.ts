import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { RejectedRegistrationMySuffix } from './rejected-registration-my-suffix.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<RejectedRegistrationMySuffix>;

@Injectable()
export class RejectedRegistrationMySuffixService {

    private resourceUrl =  SERVER_API_URL + 'api/rejected-registrations';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/rejected-registrations';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) { }

    create(rejectedRegistration: RejectedRegistrationMySuffix): Observable<EntityResponseType> {
        const copy = this.convert(rejectedRegistration);
        return this.http.post<RejectedRegistrationMySuffix>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(rejectedRegistration: RejectedRegistrationMySuffix): Observable<EntityResponseType> {
        const copy = this.convert(rejectedRegistration);
        return this.http.put<RejectedRegistrationMySuffix>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<RejectedRegistrationMySuffix>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<RejectedRegistrationMySuffix[]>> {
        const options = createRequestOption(req);
        return this.http.get<RejectedRegistrationMySuffix[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<RejectedRegistrationMySuffix[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    search(req?: any): Observable<HttpResponse<RejectedRegistrationMySuffix[]>> {
        const options = createRequestOption(req);
        return this.http.get<RejectedRegistrationMySuffix[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<RejectedRegistrationMySuffix[]>) => this.convertArrayResponse(res));
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: RejectedRegistrationMySuffix = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<RejectedRegistrationMySuffix[]>): HttpResponse<RejectedRegistrationMySuffix[]> {
        const jsonResponse: RejectedRegistrationMySuffix[] = res.body;
        const body: RejectedRegistrationMySuffix[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to RejectedRegistrationMySuffix.
     */
    private convertItemFromServer(rejectedRegistration: RejectedRegistrationMySuffix): RejectedRegistrationMySuffix {
        const copy: RejectedRegistrationMySuffix = Object.assign({}, rejectedRegistration);
        copy.dateDeNaissance = this.dateUtils
            .convertDateTimeFromServer(rejectedRegistration.dateDeNaissance);
        return copy;
    }

    /**
     * Convert a RejectedRegistrationMySuffix to a JSON which can be sent to the server.
     */
    private convert(rejectedRegistration: RejectedRegistrationMySuffix): RejectedRegistrationMySuffix {
        const copy: RejectedRegistrationMySuffix = Object.assign({}, rejectedRegistration);

        copy.dateDeNaissance = this.dateUtils.toDate(rejectedRegistration.dateDeNaissance);
        return copy;
    }
}
