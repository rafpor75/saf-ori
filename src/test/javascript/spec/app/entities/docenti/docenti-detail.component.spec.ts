/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { SafOriTestModule } from '../../../test.module';
import { DocentiDetailComponent } from '../../../../../../main/webapp/app/entities/docenti/docenti-detail.component';
import { DocentiService } from '../../../../../../main/webapp/app/entities/docenti/docenti.service';
import { Docenti } from '../../../../../../main/webapp/app/entities/docenti/docenti.model';

describe('Component Tests', () => {

    describe('Docenti Management Detail Component', () => {
        let comp: DocentiDetailComponent;
        let fixture: ComponentFixture<DocentiDetailComponent>;
        let service: DocentiService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SafOriTestModule],
                declarations: [DocentiDetailComponent],
                providers: [
                    DocentiService
                ]
            })
            .overrideTemplate(DocentiDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(DocentiDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DocentiService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new Docenti(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.docenti).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
