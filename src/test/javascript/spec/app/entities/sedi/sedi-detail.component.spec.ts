/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { SafOriTestModule } from '../../../test.module';
import { SediDetailComponent } from '../../../../../../main/webapp/app/entities/sedi/sedi-detail.component';
import { SediService } from '../../../../../../main/webapp/app/entities/sedi/sedi.service';
import { Sedi } from '../../../../../../main/webapp/app/entities/sedi/sedi.model';

describe('Component Tests', () => {

    describe('Sedi Management Detail Component', () => {
        let comp: SediDetailComponent;
        let fixture: ComponentFixture<SediDetailComponent>;
        let service: SediService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SafOriTestModule],
                declarations: [SediDetailComponent],
                providers: [
                    SediService
                ]
            })
            .overrideTemplate(SediDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(SediDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SediService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new Sedi(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.sedi).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
