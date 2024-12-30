package org.authmicroservice.enums;

public enum CustomerMessageError {
    USER_NOT_FOUND("Utilisateur introuvable"),
    USER_NOT_FOUND_WITH_ID_EQUALS("User Not found with id =  "),
    EMAIL_ALREADY_EXISTS("Email already exists."),
    ID_IS_INVALID_EQUALS("ID is invalid. ID = "),
    REGISTER_ERROR("Un Erreur c'est produit veuillez essai plus tard"),
    INTERNAL_SERVER_ERROR("Erreur serveur"),
    USER_NOT_AUTHORIZED("Non autorisé"),
    EMAIL_ALREADY_EXIST("Adresse mail déjà utilisée"),
    PASSWORD_TOKEN_EXPIRED("Demande expirée. Veuillez renvoyer une nouvelle demande de réinitialiser votre mot de passe ."),
    EMAIL_INVALID("Adresse mail invalide ."),
    FIRSTNAME_INVALID("Prénom  invalide . Il doit contenir seulement des caractères."),
    LASTNAME_INVALID("Nom  invalide, Il doit contenir seulement des caractères."),
    PASSWORD_MATCH_ERROR("Le mot de passe entré ne correspond pas au mot de passe de confirmation."),
    ACCOUNT_NOT_CONFIRMED("Votre compte n'est pas vérifié. Veuillez consultez votre email !"),
    PASSWORD_LENGTH_ERROR("Le mot de passe doit contient au moins 8 caractères."),
    PHONE_INVALID("Numéro téléphone invalide."),
    ROLE_ALREADY_EXIST("Ce rôle existe"),
    EMAIL_ACC_NOT_EXIST("Cette adresse adresse mail n'est pas enregistrer veillez s'inscrire ."),
    USER_NOT_FOUND_WITH_EMAIL_EQUALS("User Not found with email =  "),
    USER_NOT_FOUND_WITH_USERNAME_EQUALS("User Not found with username =  "),
    USERNAME_IS_REQUIRED("Username is required."),
    FIRSTNAME_IS_REQUIRED("FirsName is required."),
    LASTNAME_IS_REQUIRED("LastName is required."),
    PASSWORD_IS_REQUIRED("Password is required."),
    EMAIL_IS_REQUIRED("Email is required."),
    EMAIL_IS_INVALID("Email is invalid, please check the format."),
    PHONE_NUMBER_NOT_VALID("Phone Number Not Valid"),
    INVALID_REQUEST("Invalid request."),
    PASSWORDS_DO_NOT_MATCH("Passwords do not match.");

    private final String msg;
    CustomerMessageError(String msg){
        this.msg = msg;
    }
    public String getMessage(){
        return this.msg;
    }
}
