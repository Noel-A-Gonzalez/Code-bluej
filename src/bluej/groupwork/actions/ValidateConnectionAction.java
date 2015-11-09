/*
 This file is part of the BlueJ program. 
 Copyright (C) 1999-2009  Michael Kolling and John Rosenberg 
 
 This program is free software; you can redistribute it and/or 
 modify it under the terms of the GNU General Public License 
 as published by the Free Software Foundation; either version 2 
 of the License, or (at your option) any later version. 
 
 This program is distributed in the hope that it will be useful, 
 but WITHOUT ANY WARRANTY; without even the implied warranty of 
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the 
 GNU General Public License for more details. 
 
 You should have received a copy of the GNU General Public License 
 along with this program; if not, write to the Free Software 
 Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA. 
 
 This file is subject to the Classpath exception as provided in the  
 LICENSE.txt file that accompanied this code.
 */
package bluej.groupwork.actions;

import java.awt.Dialog;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import bluej.groupwork.TeamSettings;
import bluej.groupwork.TeamworkProvider;
import bluej.groupwork.ui.CheckConnectionDialog;
import bluej.groupwork.ui.TeamSettingsPanel;

/**
 * Test the username, password, host, etc. settings to make sure they are valid
 * 
 * @author fisker
 */
public class ValidateConnectionAction extends AbstractAction
{
    private TeamSettingsPanel teamSettingsPanel;
    private Dialog owner;
    
    public ValidateConnectionAction(String name, TeamSettingsPanel teamSettingsPanel,
            Dialog owner)
    {
        super(name);
        this.teamSettingsPanel = teamSettingsPanel;
        this.owner = owner;
    }
    
    /* (non-Javadoc)
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    public void actionPerformed(ActionEvent e)
    {
        TeamworkProvider provider = teamSettingsPanel.getSelectedProvider();
        TeamSettings settings = teamSettingsPanel.getSettings();
        
        new CheckConnectionDialog(owner, provider, settings).setVisible(true);
    }
}
