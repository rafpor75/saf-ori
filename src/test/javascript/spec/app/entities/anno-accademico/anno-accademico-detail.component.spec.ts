/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { SafOriTestModule } from '../../../test.module';
import { AnnoAccademicoDetailComponent } from '../../../../../../main/webapp/app/entities/anno-accademico/anno-accademico-detail.component';
import { AnnoAccademicoService } from '../../../../../../main/webapp/app/entities/anno-accademico/anno-accademico.service';
import { AnnoAccademico } from '../../../../../../main/webapp/app/entities/anno-accademico/anno-accademico.model';

describe('Component Tests', () => {

    describe('AnnoAccademico Management Detail Component', () => {
        let comp: AnnoAccademicoDetailComponent;
        let fixture: ComponentFixture<AnnoAccademicoDetailComponent>;
        let service: AnnoAccademicoService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SafOriTestModule],
                declarations: [AnnoAccademicoDetailComponent],
                providers: [
                    AnnoAccademicoService
                ]
            })
            .overrideTemplate(AnnoAccademicoDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(AnnoAccademicoDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AnnoAccademicoService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new AnnoAccademico(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.annoAccademico).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
