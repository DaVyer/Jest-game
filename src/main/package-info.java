/**
 * Package principal contenant le point d'entrée de l'application Jest.
 * 
 * <p>Ce package contient la classe {@link main.Main} qui initialise et lance
 * simultanément les deux interfaces utilisateur du jeu :
 * <ul>
 * <li>L'interface en ligne de commande (CLI)</li>
 * <li>L'interface graphique (GUI)</li>
 * </ul>
 * 
 * <p>Les deux interfaces partagent la même instance de {@link service.GameManager}
 * pour assurer la cohérence de l'état du jeu.</p>
 * 
 * @since 03/01/2026
 * @version 03/01/2026
 */
package main;
