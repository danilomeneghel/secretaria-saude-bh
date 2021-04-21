import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms';
import { HomeComponent } from './components/home/home.component';
import { LoginComponent } from './components/login/login.component';
import { PublicRoutingModule } from './public-routing.module';


@NgModule({
    exports: [
    ],
    declarations: [LoginComponent, HomeComponent],
    imports: [
        CommonModule,
        ReactiveFormsModule,
        PublicRoutingModule,
    ]
})
export class PublicModule { }
