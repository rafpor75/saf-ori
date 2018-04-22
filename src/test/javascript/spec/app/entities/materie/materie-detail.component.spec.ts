/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { SafOriTestModule } from '../../../test.module';
import { MaterieDetailComponent } from '../../../../../../main/webapp/app/entities/materie/materie-detail.component';
import { MaterieService } from '../../../../../../main/webapp/app/entities/materie/materie.service';
import { Materie } from '../../../../../../main/webapp/app/entities/materie/materie.model';

describe('Component Tests', () => {

    describe('Materie Management Detail Component', () => {
        let comp: MaterieDetailComponent;
        let fixture: ComponentFixture<MaterieDetailComponent>;
        let service: MaterieService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SafOriTestModule],
                declarations: [MaterieDetailComponent],
                providers: [
                    MaterieService
                ]
            })
            .overrideTemplate(MaterieDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(MaterieDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MaterieService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new Materie(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.materie).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
