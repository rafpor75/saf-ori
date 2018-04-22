/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SafOriTestModule } from '../../../test.module';
import { SediComponent } from '../../../../../../main/webapp/app/entities/sedi/sedi.component';
import { SediService } from '../../../../../../main/webapp/app/entities/sedi/sedi.service';
import { Sedi } from '../../../../../../main/webapp/app/entities/sedi/sedi.model';

describe('Component Tests', () => {

    describe('Sedi Management Component', () => {
        let comp: SediComponent;
        let fixture: ComponentFixture<SediComponent>;
        let service: SediService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SafOriTestModule],
                declarations: [SediComponent],
                providers: [
                    SediService
                ]
            })
            .overrideTemplate(SediComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(SediComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SediService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new Sedi(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.sedis[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
