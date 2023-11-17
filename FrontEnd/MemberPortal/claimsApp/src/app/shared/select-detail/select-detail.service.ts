import { Component, Injectable, OnInit } from '@angular/core';

interface hospitalDetail {
  id: string;
  name: string;
  location: string;
}

interface poilcyDetail {
  id: string;
  name: string;
}

interface benefitDetail {
  id: string;
  name: string;
}

@Injectable({
  providedIn: 'root',
})
export class SelectDetailService {
  public hospitalDetails: Array<hospitalDetail> = [
    { id: 'H1', name: 'Apollo Hospital', location: 'Delhi-Indraprastha' },
    { id: 'H2', name: 'Artemis Hospital', location: 'Gurgaon' },
    {
      id: 'H3',
      name: 'Fortis Escorts Heart Institute',
      location: 'Delhi-Okhla',
    },
    {
      id: 'H4',
      name: 'BLK Super Speciality Hospital',
      location: 'Delhi-New Delhi',
    },
    {
      id: 'H5',
      name: 'Max Superspecialty Hospital, Saket',
      location: 'Delhi-New Delhi',
    },
    {
      id: 'H6',
      name: 'Fortis Memorial Research Institute',
      location: 'Gurgaon',
    },
  ];

  public policyDetails: Array<poilcyDetail> = [
    { id: 'P1001', name: 'Health Plus Classic' },
    { id: 'P1002', name: 'Health Plus Enhanced' },
    { id: 'P1003', name: 'Health Plus Premium' },
  ];

  public benefitDetails: Array<benefitDetail> = [
    { id: 'B101', name: 'Coverage for COVID-19' },
    { id: 'B102', name: 'Coverage for hospitalization at home' },
    { id: 'B103', name: 'Ambulance charges upto 2000 covered' },
    { id: 'B104', name: 'Ambulance charges upto 3000 covered' },
    { id: 'B105', name: 'Ambulance charges upto 4000 covered' },
    {
      id: 'B106',
      name: 'Hospitalization charges for Premium Twin Sharing room covered',
    },
    { id: 'B107', name: 'Hospitalization charges for Deluxe room covered' },
    {
      id: 'B108',
      name: 'Hospitalization charges for Premium Deluxe room covered',
    },
  ];
  constructor() {}
}
