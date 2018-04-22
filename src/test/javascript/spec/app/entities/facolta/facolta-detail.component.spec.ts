/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { SafOriTestModule } from '../../../test.module';
import { FacoltaDetailComponent } from '../../../../../../main/webapp/app/entities/facolta/facolta-detail.component';
import { FacoltaService } from '../../../../../../main/webapp/app/entities/facolta/facolta.service';
import { Facolta } from '../../../../../../main/webapp/app/entities/facolta/facolta.model';

describe('Component Tests', () => {

    describe('Facolta Management Detail Component', () => {
        let comp: FacoltaDetailComponent;
        let fixture: ComponentFixture<FacoltaDetailComponent>;
        let service: FacoltaService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SafOriTestModule],
                declarations: [FacoltaDetailComponent],
                providers: [
                    FacoltaService
                ]
            })
            .overrideTemplate(FacoltaDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(FacoltaDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(FacoltaService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new Facolta(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.facolta).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
