import { Component } from '@angular/core';

@Component({
  selector: 'app-test',
  templateUrl: './test.component.html',
  styleUrls: ['./test.component.css']
})
export class TestComponent {

  
  switchValue = 'INACTIVE'; // Initialise switchValue à 'INACTIVE'

  // Fonction appelée lorsqu'il y a un changement dans le switch
  onSwitchChange(value: boolean) {
    this.switchValue = value ? 'ACTIVE' : 'INACTIVE'; // Met à jour switchValue en fonction de la valeur du switch
  }

  
}
