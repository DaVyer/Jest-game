/**
 * Package contenant les interfaces graphiques du jeu.
 * 
 * <p>Ce package fournit une interface graphique complète basée sur Swing
 * pour interagir avec le jeu Jest. L'interface graphique fonctionne en
 * parallèle avec l'interface en ligne de commande et partage le même
 * état de jeu via le {@link service.GameManager}.</p>
 * 
 * <p><strong>Composants principaux :</strong></p>
 * <ul>
 * <li>{@link view.JestGUI} : Fenêtre principale avec tous les contrôles
 *     (nouvelle partie, charger, sauvegarder, jouer une manche, résultats)</li>
 * <li>{@link view.GuiInputProvider} : Implémentation graphique de 
 *     {@link model.InputProvider} utilisant des dialogues JOptionPane
 *     pour les interactions utilisateur</li>
 * </ul>
 * 
 * <p><strong>Fonctionnalités :</strong></p>
 * <ul>
 * <li>Affichage visuel des cartes avec leurs images</li>
 * <li>Dialogues interactifs pour les choix (cartes, offres)</li>
 * <li>Affichage des trophées en temps réel</li>
 * <li>Fenêtre de résultats finaux avec classement</li>
 * <li>Support des règles de base et variante</li>
 * <li>Support de l'extension Miroir</li>
 * </ul>
 * 
 * @since 03/01/2026
 * @version 03/01/2026
 */
package view;
