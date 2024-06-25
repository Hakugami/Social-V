import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DirectMessageMembersComponent } from './direct-message-members.component';

describe('DirectMessageMembersComponent', () => {
  let component: DirectMessageMembersComponent;
  let fixture: ComponentFixture<DirectMessageMembersComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DirectMessageMembersComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(DirectMessageMembersComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
