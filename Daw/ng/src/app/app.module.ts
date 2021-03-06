import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';
import { JsonpModule, HttpModule } from '@angular/http';
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';
import { MatIconRegistry } from '@angular/material/icon';

import {MatExpansionModule} from '@angular/material/expansion';

import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { RouterModule } from '@angular/router';

import { AppComponent } from './app.component';

import {
    MatButtonModule,
    MatCheckboxModule,
    MatListModule,
    MatIconModule,
    MatCardModule,
    MatMenuModule,
    MatInputModule,
    MatButtonToggleModule,
    MatProgressSpinnerModule,
    MatSelectModule,
    MatSlideToggleModule,
    MatDialogModule,
    MatSnackBarModule,
    MatToolbarModule,
    MatTabsModule,
    MatSidenavModule,
    MatTooltipModule,
    MatRippleModule,
    MatRadioModule,
    MatGridListModule,
    MatDatepickerModule,
    MatNativeDateModule,
    MatSliderModule,
    MatAutocompleteModule,
} from '@angular/material';

import {
    CovalentCommonModule,
    CovalentLayoutModule,
    CovalentMediaModule,
    CovalentExpansionPanelModule,
    CovalentStepsModule,
    CovalentLoadingModule,
    CovalentDialogsModule,
    CovalentSearchModule,
    CovalentPagingModule,
    CovalentNotificationsModule,
    CovalentMenuModule,
    CovalentDataTableModule,
    CovalentMessageModule,
} from '@covalent/core';



import { NgxChartsModule } from '@swimlane/ngx-charts';
import { DomSanitizer } from '@angular/platform-browser';

import {LoginComponent} from "./login.component";
import { routing } from './app.routing';
import { MainpageComponent } from './views/mainpage/mainpage.component';
import { TeacherConceptViewComponent } from './views/teacher-concept-view/teacher-concept-view.component';
import { StudentConceptViewComponent } from './views/student-concept-view/student-concept-view.component';

import {UserService} from './service/user-service';
import {QuestionService} from "./service/question-service";
import {LessonService} from './service/lesson-service';
import {LoginService} from "./auth/login.service";
import {ItemService} from "./service/item-service";
import {ConceptService} from "./service/concept-service";

import { CovalentBarEchartsModule } from '@covalent/echarts/bar';
import { CovalentBaseEchartsModule } from '@covalent/echarts/base';
import { CovalentTooltipEchartsModule } from '@covalent/echarts/tooltip';
import { CovalentToolboxEchartsModule } from '@covalent/echarts/toolbox';

import { LocationStrategy, HashLocationStrategy } from '@angular/common';
import { ErrorInterceptor } from './auth/error.interceptor';
import { BasicAuthInterceptor } from './auth/auth.interceptor';
import {AnswerService} from "./service/answer-service";
import {MatPaginatorModule} from '@angular/material/paginator';

@NgModule({
    declarations: [AppComponent,
        LoginComponent,
        MainpageComponent,
        TeacherConceptViewComponent,
        StudentConceptViewComponent,],
    imports: [
        BrowserModule,
        BrowserAnimationsModule,
        FormsModule,
        RouterModule.forRoot([]),
        HttpClientModule,
        JsonpModule,
        /** Material Modules */
        MatButtonModule,
        MatListModule,
        MatCheckboxModule,
        MatIconModule,
        MatCardModule,
        MatMenuModule,
        MatInputModule,
        MatPaginatorModule,
        MatSelectModule,
        MatButtonToggleModule,
        MatSlideToggleModule,
        MatProgressSpinnerModule,
        MatDialogModule,
        MatIconModule,
        MatSnackBarModule,
        MatToolbarModule,
        MatTabsModule,
        MatSidenavModule,
        MatTooltipModule,
        MatRippleModule,
        MatRadioModule,
        MatGridListModule,
        MatDatepickerModule,
        MatNativeDateModule,
        MatSliderModule,
        MatAutocompleteModule,
        MatExpansionModule,
        /** Covalent Modules */
        CovalentCommonModule,
        CovalentLayoutModule,
        CovalentMediaModule,
        CovalentExpansionPanelModule,
        CovalentStepsModule,
        CovalentDialogsModule,
        CovalentLoadingModule,
        CovalentSearchModule,
        CovalentPagingModule,
        CovalentNotificationsModule,
        CovalentMenuModule,
        CovalentDataTableModule,
        CovalentMessageModule,
        /** Additional **/
        NgxChartsModule,
        CovalentBaseEchartsModule,
        CovalentBarEchartsModule,
        CovalentTooltipEchartsModule,
        CovalentToolboxEchartsModule,
        HttpModule, //Remove when migrated to HttpClient
        routing
    ],
    bootstrap: [AppComponent],
    providers: [LoginService, UserService, LessonService,ItemService,ConceptService,QuestionService,AnswerService,
        {provide: LocationStrategy,useClass:HashLocationStrategy},
        {provide: HTTP_INTERCEPTORS,useClass: BasicAuthInterceptor,multi:true},
        {provide: HTTP_INTERCEPTORS,useClass: ErrorInterceptor,multi:true}
        ]
})
export class AppModule {
    constructor(private matIconRegistry: MatIconRegistry, private domSanitizer: DomSanitizer) {
        matIconRegistry.addSvgIconSet(domSanitizer.bypassSecurityTrustResourceUrl('/assets/symbol-defs.svg'));
    }
}
