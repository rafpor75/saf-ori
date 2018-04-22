/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SafOriTestModule } from '../../../test.module';
import { CdlComponent } from '../../../../../../main/webapp/app/entities/cdl/cdl.component';
import { CdlService } from '../../../../../../main/webapp/app/entities/cdl/cdl.service';
import { Cdl } from '../../../../../../main/webapp/app/entities/cdl/cdl.model';

describe('Component Tests', () => {

    describe('Cdl Management Component', () => {
        let comp: CdlComponent;
        let fixture: ComponentFixture<CdlComponent>;
        let service: CdlService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SafOriTestModule],
                declarations: [CdlComponent],
                providers: [
                    CdlService
                ]
            })
            .overrideTemplate(CdlComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CdlComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CdlService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new Cdl(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.cdls[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
