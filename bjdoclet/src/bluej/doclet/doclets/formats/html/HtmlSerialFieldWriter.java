/*
 * Copyright (c) 1998, 2006, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the LICENSE file that accompanied this code.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 * 
 * --- end of original header ---
 * 
 * This file was modified for use in the BlueJ program on the 1st September 2011.
 * 
 */

package bluej.doclet.doclets.formats.html;

import java.util.List;

import bluej.doclet.doclets.internal.toolkit.SerializedFormWriter;
import bluej.doclet.doclets.internal.toolkit.taglets.DeprecatedTaglet;
import bluej.doclet.doclets.internal.toolkit.taglets.TagletWriter;
import bluej.doclet.doclets.internal.toolkit.util.Util;

import com.sun.javadoc.ClassDoc;
import com.sun.javadoc.FieldDoc;
import com.sun.javadoc.ProgramElementDoc;
import com.sun.javadoc.SerialFieldTag;
import com.sun.javadoc.Tag;
import com.sun.javadoc.Type;

/**
 * Generate serialized form for serializable fields.
 * Documentation denoted by the tags <code>serial</code> and
 * <code>serialField</code> is processed.
 *
 * @author Joe Fialli
 */
public class HtmlSerialFieldWriter extends FieldWriterImpl
    implements SerializedFormWriter.SerialFieldWriter {
    ProgramElementDoc[] members = null;

    private boolean printedOverallAnchor = false;

    private boolean printedFirstMember = false;

    public HtmlSerialFieldWriter(SubWriterHolderWriter writer,
                                    ClassDoc classdoc) {
        super(writer, classdoc);
    }

    public List members(ClassDoc cd) {
        return Util.asList(cd.serializableFields());
    }

    protected void printTypeLinkNoDimension(Type type) {
        ClassDoc cd = type.asClassDoc();
        //Linking to package private classes in serialized for causes
        //broken links.  Don't link to them.
        if (type.isPrimitive() || cd.isPackagePrivate()) {
            print(type.typeName());
        } else {
            writer.printLink(new LinkInfoImpl(
                LinkInfoImpl.CONTEXT_SERIAL_MEMBER, type));
        }
    }

    public void writeHeader(String heading) {
        if (! printedOverallAnchor) {
            writer.anchor("serializedForm");
            printedOverallAnchor = true;
            writer.printTableHeadingBackground(heading);
            writer.println();
            if (heading.equals(
                   configuration().getText("doclet.Serialized_Form_class"))) {
                writer.dl();
            }
        } else {
            writer.printTableHeadingBackground(heading);
            writer.println();
        }
    }

    public void writeMemberHeader(ClassDoc fieldType, String fieldTypeStr,
            String fieldDimensions, String fieldName) {
        if (printedFirstMember) {
            writer.printMemberHeader();
        }
        printedFirstMember = true;
        writer.h3();
        writer.print(fieldName);
        writer.h3End();
        writer.pre();
        if (fieldType == null) {
            writer.print(fieldTypeStr);
        } else {
            writer.printLink(new LinkInfoImpl(LinkInfoImpl.CONTEXT_SERIAL_MEMBER,
                fieldType));
        }
        print(fieldDimensions + ' ');
        bold(fieldName);
        writer.preEnd();
        writer.dl();
    }

    /**
     * Write the deprecated information for this member.
     *
     * @param field the field to document.
     */
    public void writeMemberDeprecatedInfo(FieldDoc field) {
        print(((TagletOutputImpl)
            (new DeprecatedTaglet()).getTagletOutput(field,
            writer.getTagletWriterInstance(false))).toString());
    }

    /**
     * Write the description text for this member.
     *
     * @param field the field to document.
     */
    public void writeMemberDescription(FieldDoc field) {
        if (field.inlineTags().length > 0) {
            writer.dd();
            writer.printInlineComment(field);
        }
        Tag[] tags = field.tags("serial");
        if (tags.length > 0) {
            writer.dt();
            writer.dd();
            writer.printInlineComment(field, tags[0]);
        }
    }

    /**
     * Write the description text for this member represented by the tag.
     *
     * @param serialFieldTag the field to document (represented by tag).
     */
    public void writeMemberDescription(SerialFieldTag serialFieldTag) {
        writer.dd();
        writer.print(serialFieldTag.description());
        writer.dlEnd();
    }

    /**
     * Write the tag information for this member.
     *
     * @param field the field to document.
     */
    public void writeMemberTags(FieldDoc field) {
        writer.dl();
        TagletOutputImpl output = new TagletOutputImpl("");
        TagletWriter.genTagOuput(configuration().tagletManager, field,
            configuration().tagletManager.getCustomTags(field),
                writer.getTagletWriterInstance(false), output);
        if (output.toString().length() > 0) {
            print(output.toString());
        }
        writer.dlEnd();
    }
    public void writeMemberFooter(FieldDoc member) {
        writer.dlEnd();
    }
}
