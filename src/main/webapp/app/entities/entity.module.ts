import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { SafOriFacoltaModule } from './facolta/facolta.module';
import { SafOriAnnoAccademicoModule } from './anno-accademico/anno-accademico.module';
import { SafOriCdlModule } from './cdl/cdl.module';
import { SafOriDocentiModule } from './docenti/docenti.module';
import { SafOriMaterieModule } from './materie/materie.module';
import { SafOriPianiDiStudioModule } from './piani-di-studio/piani-di-studio.module';
import { SafOriSediModule } from './sedi/sedi.module';
import { SafOriStudentiModule } from './studenti/studenti.module';
import { SafOriTutorModule } from './tutor/tutor.module';
import { SafOriEsamiModule } from './esami/esami.module';
import { SafOriNoteEsameModule } from './note-esame/note-esame.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        SafOriFacoltaModule,
        SafOriAnnoAccademicoModule,
        SafOriCdlModule,
        SafOriDocentiModule,
        SafOriMaterieModule,
        SafOriPianiDiStudioModule,
        SafOriSediModule,
        SafOriStudentiModule,
        SafOriTutorModule,
        SafOriEsamiModule,
        SafOriNoteEsameModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SafOriEntityModule {}
