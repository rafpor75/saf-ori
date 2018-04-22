/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { SafOriTestModule } from '../../../test.module';
import { CdlDetailComponent } from '../../../../../../main/webapp/app/entities/cdl/cdl-detail.component';
import { CdlService } from '../../../../../../main/webapp/app/entities/cdl/cdl.service';
import { Cdl } from '../../../../../../main/webapp/app/entities/cdl/cdl.model';

describe('Component Tests', () => {

    describe('Cdl Management Detail Component', () => {
        let comp: CdlDetailComponent;
        let fixture: ComponentFixture<CdlDetailComponent>;
        let service: CdlService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SafOriTestModule],
                declarations: [CdlDetailComponent],
                providers: [
                    CdlService
                ]
            })
            .overrideTemplate(CdlDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CdlDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CdlService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new Cdl(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.cdl).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
