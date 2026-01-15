/**
 * Package contenant les services de gestion de la partie.
 * 
 * <p>Ce package fournit une couche de service pour gérer l'état global
 * de l'application. Le {@link service.GameManager} agit comme un point
 * de synchronisation entre les différentes interfaces (CLI et GUI),
 * garantissant qu'une seule partie est active à la fois et que toutes
 * les opérations sont thread-safe.</p>
 * 
 * <p><strong>Responsabilités :</strong></p>
 * <ul>
 * <li>Gestion du cycle de vie de la partie (création, chargement, terminaison)</li>
 * <li>Synchronisation multi-thread entre CLI et GUI</li>
 * <li>Accès centralisé à l'état de la partie courante</li>
 * </ul>
 * 
 * @since 03/01/2026
 * @version 03/01/2026
 */
package service;
