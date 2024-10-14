import { Routes } from '@angular/router';
import { LoginComponent } from './components/layout/login/login.component';
import { PrincipalComponent } from './components/layout/principal/principal.component';
import { EmployeelistComponent } from './components/employee/employeelist/employeelist.component';
import { EmployeedetailsComponent } from './components/employee/employeedetails/employeedetails.component';

export const routes: Routes = [
  { path: '', redirectTo: 'login', pathMatch: 'full' },
  { path: 'login', component: LoginComponent },
  {
    path: 'admin',
    component: PrincipalComponent,
    children: [
      { path: 'employee', component: EmployeelistComponent },
      { path: 'employee/new', component: EmployeedetailsComponent },
      { path: 'employee/edit/:id', component: EmployeedetailsComponent },
    ],
  },
];
