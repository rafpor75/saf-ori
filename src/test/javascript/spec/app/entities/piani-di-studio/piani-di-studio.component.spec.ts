/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SafOriTestModule } from '../../../test.module';
import { PianiDiStudioComponent } from '../../../../../../main/webapp/app/entities/piani-di-studio/piani-di-studio.component';
import { PianiDiStudioService } from '../../../../../../main/webapp/app/entities/piani-di-studio/piani-di-studio.service';
import { PianiDiStudio } from '../../../../../../main/webapp/app/entities/piani-di-studio/piani-di-studio.model';

describe('Component Tests', () => {

    describe('PianiDiStudio Management Component', () => {
        let comp: PianiDiStudioComponent;
        let fixture: ComponentFixture<PianiDiStudioComponent>;
        let service: PianiDiStudioService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SafOriTestModule],
                declarations: [PianiDiStudioComponent],
                providers: [
                    PianiDiStudioService
                ]
            })
            .overrideTemplate(PianiDiStudioComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PianiDiStudioComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PianiDiStudioService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new PianiDiStudio(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.pianiDiStudios[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
