export class Recipe {
    id: string
    name: string
    user: string

    constructor(theId: string, theName: string, theUser: string) {
        this.id = theId;
        this.name = theName;
        this.user = theUser;
    }
}