/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { SafOriTestModule } from '../../../test.module';
import { StudentiDetailComponent } from '../../../../../../main/webapp/app/entities/studenti/studenti-detail.component';
import { StudentiService } from '../../../../../../main/webapp/app/entities/studenti/studenti.service';
import { Studenti } from '../../../../../../main/webapp/app/entities/studenti/studenti.model';

describe('Component Tests', () => {

    describe('Studenti Management Detail Component', () => {
        let comp: StudentiDetailComponent;
        let fixture: ComponentFixture<StudentiDetailComponent>;
        let service: StudentiService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SafOriTestModule],
                declarations: [StudentiDetailComponent],
                providers: [
                    StudentiService
                ]
            })
            .overrideTemplate(StudentiDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(StudentiDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(StudentiService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new Studenti(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.studenti).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
