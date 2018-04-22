/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SafOriTestModule } from '../../../test.module';
import { StudentiComponent } from '../../../../../../main/webapp/app/entities/studenti/studenti.component';
import { StudentiService } from '../../../../../../main/webapp/app/entities/studenti/studenti.service';
import { Studenti } from '../../../../../../main/webapp/app/entities/studenti/studenti.model';

describe('Component Tests', () => {

    describe('Studenti Management Component', () => {
        let comp: StudentiComponent;
        let fixture: ComponentFixture<StudentiComponent>;
        let service: StudentiService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SafOriTestModule],
                declarations: [StudentiComponent],
                providers: [
                    StudentiService
                ]
            })
            .overrideTemplate(StudentiComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(StudentiComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(StudentiService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new Studenti(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.studentis[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
