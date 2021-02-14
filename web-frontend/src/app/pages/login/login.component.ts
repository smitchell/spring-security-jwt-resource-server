import {Component, Input, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {
  loginForm: FormGroup;
  invalidLogin = false;
  showSpinner = false;

  @Input() error: string | null;

  constructor(fb: FormBuilder, private router: Router) {
    this.loginForm = fb.group({
      username: ['', Validators.required],
      password: ['', Validators.required]
    });
  }

  ngOnInit(): void {
  }

  onSubmit(): void {
    const username = this.loginForm.get('username').value;
    const password = this.loginForm.get('password').value;
    console.log(`username = "${username}" password = "${password}"`);
    this.router.navigate(['']);
  }
}
