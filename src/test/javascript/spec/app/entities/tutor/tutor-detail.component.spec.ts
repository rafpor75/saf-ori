/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { SafOriTestModule } from '../../../test.module';
import { TutorDetailComponent } from '../../../../../../main/webapp/app/entities/tutor/tutor-detail.component';
import { TutorService } from '../../../../../../main/webapp/app/entities/tutor/tutor.service';
import { Tutor } from '../../../../../../main/webapp/app/entities/tutor/tutor.model';

describe('Component Tests', () => {

    describe('Tutor Management Detail Component', () => {
        let comp: TutorDetailComponent;
        let fixture: ComponentFixture<TutorDetailComponent>;
        let service: TutorService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SafOriTestModule],
                declarations: [TutorDetailComponent],
                providers: [
                    TutorService
                ]
            })
            .overrideTemplate(TutorDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(TutorDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TutorService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new Tutor(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.tutor).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
