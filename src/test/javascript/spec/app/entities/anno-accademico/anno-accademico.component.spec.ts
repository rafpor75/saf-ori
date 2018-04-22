/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SafOriTestModule } from '../../../test.module';
import { AnnoAccademicoComponent } from '../../../../../../main/webapp/app/entities/anno-accademico/anno-accademico.component';
import { AnnoAccademicoService } from '../../../../../../main/webapp/app/entities/anno-accademico/anno-accademico.service';
import { AnnoAccademico } from '../../../../../../main/webapp/app/entities/anno-accademico/anno-accademico.model';

describe('Component Tests', () => {

    describe('AnnoAccademico Management Component', () => {
        let comp: AnnoAccademicoComponent;
        let fixture: ComponentFixture<AnnoAccademicoComponent>;
        let service: AnnoAccademicoService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SafOriTestModule],
                declarations: [AnnoAccademicoComponent],
                providers: [
                    AnnoAccademicoService
                ]
            })
            .overrideTemplate(AnnoAccademicoComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(AnnoAccademicoComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AnnoAccademicoService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new AnnoAccademico(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.annoAccademicos[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
