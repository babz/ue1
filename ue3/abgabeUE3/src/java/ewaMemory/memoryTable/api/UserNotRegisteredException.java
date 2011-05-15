/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ewaMemory.memoryTable.api;

/**
 *
 * @author stephan
 */
class UserNotRegisteredException extends Exception {

    public UserNotRegisteredException(String name) {
        super(name);
    }

}
