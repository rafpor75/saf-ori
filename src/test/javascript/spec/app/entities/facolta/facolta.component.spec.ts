/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SafOriTestModule } from '../../../test.module';
import { FacoltaComponent } from '../../../../../../main/webapp/app/entities/facolta/facolta.component';
import { FacoltaService } from '../../../../../../main/webapp/app/entities/facolta/facolta.service';
import { Facolta } from '../../../../../../main/webapp/app/entities/facolta/facolta.model';

describe('Component Tests', () => {

    describe('Facolta Management Component', () => {
        let comp: FacoltaComponent;
        let fixture: ComponentFixture<FacoltaComponent>;
        let service: FacoltaService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SafOriTestModule],
                declarations: [FacoltaComponent],
                providers: [
                    FacoltaService
                ]
            })
            .overrideTemplate(FacoltaComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(FacoltaComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(FacoltaService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new Facolta(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.facoltas[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
