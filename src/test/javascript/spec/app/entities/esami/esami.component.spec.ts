/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SafOriTestModule } from '../../../test.module';
import { EsamiComponent } from '../../../../../../main/webapp/app/entities/esami/esami.component';
import { EsamiService } from '../../../../../../main/webapp/app/entities/esami/esami.service';
import { Esami } from '../../../../../../main/webapp/app/entities/esami/esami.model';

describe('Component Tests', () => {

    describe('Esami Management Component', () => {
        let comp: EsamiComponent;
        let fixture: ComponentFixture<EsamiComponent>;
        let service: EsamiService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SafOriTestModule],
                declarations: [EsamiComponent],
                providers: [
                    EsamiService
                ]
            })
            .overrideTemplate(EsamiComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(EsamiComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EsamiService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new Esami(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.esamis[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
