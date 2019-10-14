import { Component, OnInit, Injectable, Input } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Router } from '@angular/router/';
import { CartService } from '../cart/cart-service';

@Component({
  selector: 'pet-design',
  templateUrl: 'design.component.html',
  styleUrls: ['./design.component.css']
})

@Injectable()
export class DesignComponent implements OnInit {

  model = {
    name: '',
    species: []
  };

  allSpecies: any;
  animals = [];
  genders = [];
  colours = [];
  details = [];
  personalitys = [];

  constructor(private httpClient: HttpClient, private router: Router, private cart: CartService) {
  }

  // tag::ngOnInit[]
  ngOnInit() {
    this.httpClient.get('http://localhost:8080/species')
        .subscribe(data => {
          this.allSpecies = data;
          this.animals = this.allSpecies.filter(w => w.type === 'ANIMAL');
          this.genders = this.allSpecies.filter(p => p.type === 'GENDER');
          this.colours = this.allSpecies.filter(v => v.type === 'COLOUR');
          this.details = this.allSpecies.filter(c => c.type === 'UNIQUE_DETAIL');
          this.personalitys = this.allSpecies.filter(s => s.type === 'PERSONALITY');
        });
  }
  // end::ngOnInit[]

  updateSpecies(specie, event) {
    if (event.target.checked) {
      this.model.species.push(specie);
    } else {
      this.model.species.splice(this.model.species.findIndex(i => i === specie), 1);
    }
  }

  // tag::onSubmit[]
  onSubmit() {
    this.httpClient.post(
        'http://localhost:8080/design',
        this.model, {
            headers: new HttpHeaders().set('Content-type', 'application/json'),
        }).subscribe(pet => this.cart.addToCart(pet));

    this.router.navigate(['/cart']);
  }
  // end::onSubmit[]

}
