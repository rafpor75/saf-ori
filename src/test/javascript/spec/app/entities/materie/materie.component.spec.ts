/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SafOriTestModule } from '../../../test.module';
import { MaterieComponent } from '../../../../../../main/webapp/app/entities/materie/materie.component';
import { MaterieService } from '../../../../../../main/webapp/app/entities/materie/materie.service';
import { Materie } from '../../../../../../main/webapp/app/entities/materie/materie.model';

describe('Component Tests', () => {

    describe('Materie Management Component', () => {
        let comp: MaterieComponent;
        let fixture: ComponentFixture<MaterieComponent>;
        let service: MaterieService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SafOriTestModule],
                declarations: [MaterieComponent],
                providers: [
                    MaterieService
                ]
            })
            .overrideTemplate(MaterieComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(MaterieComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MaterieService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new Materie(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.materies[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
