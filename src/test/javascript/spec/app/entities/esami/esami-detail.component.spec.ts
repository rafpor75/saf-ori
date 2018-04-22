/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { SafOriTestModule } from '../../../test.module';
import { EsamiDetailComponent } from '../../../../../../main/webapp/app/entities/esami/esami-detail.component';
import { EsamiService } from '../../../../../../main/webapp/app/entities/esami/esami.service';
import { Esami } from '../../../../../../main/webapp/app/entities/esami/esami.model';

describe('Component Tests', () => {

    describe('Esami Management Detail Component', () => {
        let comp: EsamiDetailComponent;
        let fixture: ComponentFixture<EsamiDetailComponent>;
        let service: EsamiService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SafOriTestModule],
                declarations: [EsamiDetailComponent],
                providers: [
                    EsamiService
                ]
            })
            .overrideTemplate(EsamiDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(EsamiDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EsamiService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new Esami(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.esami).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
