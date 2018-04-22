/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SafOriTestModule } from '../../../test.module';
import { DocentiComponent } from '../../../../../../main/webapp/app/entities/docenti/docenti.component';
import { DocentiService } from '../../../../../../main/webapp/app/entities/docenti/docenti.service';
import { Docenti } from '../../../../../../main/webapp/app/entities/docenti/docenti.model';

describe('Component Tests', () => {

    describe('Docenti Management Component', () => {
        let comp: DocentiComponent;
        let fixture: ComponentFixture<DocentiComponent>;
        let service: DocentiService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SafOriTestModule],
                declarations: [DocentiComponent],
                providers: [
                    DocentiService
                ]
            })
            .overrideTemplate(DocentiComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(DocentiComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DocentiService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new Docenti(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.docentis[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
