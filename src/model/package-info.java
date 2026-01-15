/**
 * Package contenant le modèle métier du jeu Jest.
 * 
 * <p>Ce package implémente toute la logique métier du jeu Jest, incluant
 * les règles du jeu, le calcul des scores, l'attribution des trophées
 * et la gestion de l'état de la partie.</p>
 * 
 * <p><strong>Structure du modèle :</strong></p>
 * <ul>
 * <li><strong>Entités principales :</strong> {@link model.Carte}, {@link model.Jest}, 
 *     {@link model.Joueur}, {@link model.Partie}, {@link model.Manche}, {@link model.Offre}</li>
 * <li><strong>Enumerations :</strong> {@link model.CouleurCarte}, {@link model.ValeurCarte}, 
 *     {@link model.ConditionTrophee}</li>
 * <li><strong>Stratégies de jeu :</strong> {@link model.StrategieJoueur}, 
 *     {@link model.StrategieHumaine}, {@link model.StrategieRobotAleatoire}</li>
 * <li><strong>Visiteurs (pattern Visitor) :</strong> {@link model.ScoreVisitor}, 
 *     {@link model.TropheeVisitor}</li>
 * <li><strong>Persistance :</strong> {@link model.Save}, {@link model.Load}, 
 *     classes DTO et {@link model.DTOMapper}</li>
 * <li><strong>Entrées utilisateur :</strong> {@link model.InputProvider}, 
 *     {@link model.ConsoleInputProvider}</li>
 * </ul>
 * 
 * <p><strong>Patterns utilisés :</strong></p>
 * <ul>
 * <li><strong>Strategy :</strong> Pour les différentes stratégies de jeu (humain/robot)</li>
 * <li><strong>Visitor :</strong> Pour le calcul des scores et l'attribution des trophées</li>
 * <li><strong>DTO (Data Transfer Object) :</strong> Pour la sérialisation</li>
 * </ul>
 * 
 * @since 03/01/2026
 * @version 03/01/2026
 */
package model;
