/*
 * Encog(tm) Workbanch v3.1 - Java Version
 * http://www.heatonresearch.com/encog/
 * http://code.google.com/p/encog-java/
 
 * Copyright 2008-2012 Heaton Research, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *   
 * For more information on Heaton Research copyrights, licenses 
 * and trademarks visit:
 * http://www.heatonresearch.com/copyright
 */
package org.encog.workbench.dialogs.wizard.analyst;

import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JComboBox;

import org.encog.app.analyst.AnalystGoal;
import org.encog.app.analyst.wizard.NormalizeRange;
import org.encog.app.analyst.wizard.WizardMethodType;
import org.encog.workbench.EncogWorkBench;
import org.encog.workbench.dialogs.common.BuildingListField;
import org.encog.workbench.dialogs.common.CheckField;
import org.encog.workbench.dialogs.common.ComboBoxField;
import org.encog.workbench.dialogs.common.DoubleField;
import org.encog.workbench.dialogs.common.EncogPropertiesDialog;
import org.encog.workbench.dialogs.common.IntegerField;
import org.encog.workbench.dialogs.common.TextField;

public class RealTimeAnalystWizardDialog extends EncogPropertiesDialog {
	
	private BuildingListField sourceData;
	private final TextField baseName;
	private final ComboBoxField method;
	private final ComboBoxField range;
	private final ComboBoxField goal;
	private final ComboBoxField missing;
	private final IntegerField lagCount;
	private final IntegerField leadCount;
	private final CheckField normalize;
	private final CheckField segregate;
	private final DoubleField maxError;
	
	private final List<String> methods = new ArrayList<String>();
	
	public RealTimeAnalystWizardDialog() {
		super(EncogWorkBench.getInstance().getMainWindow());
		
		List<String> list = new ArrayList<String>();
		list.add("CSV");
				
		List<String> goalList = new ArrayList<String>();
		goalList.add("Classification");
		goalList.add("Regression");
		
		List<String> rangeList = new ArrayList<String>();
		rangeList.add("-1 to 1");
		rangeList.add("0 to 1");
		
		List<String> missingList = new ArrayList<String>();
		missingList.add("DiscardMissing");
		missingList.add("MeanAndModeMissing");
		missingList.add("NegateMissing");

		methods.add("Bayesian Network");
		methods.add("Feedforward Network");
		methods.add("RBF Network");
		methods.add("PNN/GRNN Network");
		methods.add("Self Organizing Map (SOM)");
		methods.add("Support Vector Machine");		
				
		this.setSize(640, 360);
		this.setTitle("Realtime Encog Analyst Wizard");
		
		beginTab("Data");
		addProperty(this.baseName = new TextField("ega file","EGA File Name",true));
		addProperty(this.sourceData = new BuildingListField("source fields","Source Fields"));
		
		beginTab("Machine Learning");
		addProperty(this.method = new ComboBoxField("method", "Machine Learning", true, methods));
		addProperty(this.range = new ComboBoxField("normalization range", "Normalization Range", true, rangeList));
		addProperty(this.missing = new ComboBoxField("missing values", "Missing Values", true, missingList));
		addProperty(this.maxError = new DoubleField("max error","Maximum Error Percent(0-100)", true, 0, 100));

		beginTab("Goal");
		addProperty(this.goal = new ComboBoxField("goal", "Goal", true, goalList));
		addProperty(this.lagCount = new IntegerField("lag count","Lag Count",true,0,1000));
		addProperty(this.leadCount = new IntegerField("lead count","Lead Count",true,0,1000));

		beginTab("Tasks");
		addProperty(this.normalize = new CheckField("normalize","Normalize"));
		addProperty(this.segregate = new CheckField("segregate","Segregate"));
				
		render();
		
		this.lagCount.setValue(0);
		this.leadCount.setValue(0);
		
		this.segregate.setValue(true);
		this.normalize.setValue(true);
		this.sourceData.getModel().addElement("HIGH");
		this.sourceData.getModel().addElement("LOW");
		this.sourceData.getModel().addElement("OPEN");
		this.sourceData.getModel().addElement("CLOSE");
		this.sourceData.getModel().addElement("VOL");
		((JComboBox)this.method.getField()).setSelectedIndex(1);
		this.getMaxError().setValue(EncogWorkBench.getInstance().getConfig().getDefaultError());
	
	}
	
	public WizardMethodType getMethodType()
	{
		switch(this.method.getSelectedIndex()) {
			case 0:
				return WizardMethodType.BayesianNetwork;
			case 1:
				return WizardMethodType.FeedForward;
			case 2:
				return WizardMethodType.RBF;
			case 3:
				return WizardMethodType.PNN;
			case 4:
				return WizardMethodType.SOM;
			case 5:
				return WizardMethodType.SVM;
			default:
				return null;
		}
	}
		
	public AnalystGoal getGoal() {
		int idx = this.goal.getSelectedIndex();
		switch(idx) {
			case 0:
				return AnalystGoal.Classification;
			case 1:
				return AnalystGoal.Regression;
			default:
				return null;
		}
	}
	
	public NormalizeRange getRange() {
		int idx = this.range.getSelectedIndex();
		switch(idx) {
			case 0:
				return NormalizeRange.NegOne2One;
			case 1:
				return NormalizeRange.Zero2One;
			default:
				return null;
		}
	}
	
	/**
	 * @return the lagCount
	 */
	public IntegerField getLagCount() {
		return lagCount;
	}

	/**
	 * @return the leadCount
	 */
	public IntegerField getLeadCount() {
		return leadCount;
	}

	/**
	 * @return the segregate
	 */
	public CheckField getSegregate() {
		return segregate;
	}

	/**
	 * @return the normalize
	 */
	public CheckField getNormalize() {
		return normalize;
	}

	/**
	 * @return the missing
	 */
	public ComboBoxField getMissing() {
		return missing;
	}	
	
	public DoubleField getMaxError() {
		return this.maxError;
	}


	/**
	 * @return the egaFile
	 */
	public TextField getBaseName() {
		return baseName;
	}
	
	public List<String> getSourceData() {
		DefaultListModel ctrl = this.sourceData.getModel();
		List<String> result = new ArrayList<String>();
		for(int i=0; i<ctrl.getSize();i++)
		{
			result.add(ctrl.get(i).toString());
		}
		return result;
	}
	
	
}
