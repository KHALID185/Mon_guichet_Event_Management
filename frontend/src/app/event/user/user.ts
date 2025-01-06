// user.ts

export interface User {
    userId: number;
    firstname: string;
    lastname: string;
    email: string;
    numberPhone: string;
    isActive: string;
    roles: Role[];
  }
  
  interface Role {
    role: string;
  }  
  